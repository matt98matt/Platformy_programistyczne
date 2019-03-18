using Microsoft.Win32;
using System;
using System.Collections.Generic;
using System.Collections.ObjectModel;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using System.IO;
using System.Net.Http;
using System.Xml;

namespace Lab01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    ///
    ///

    class WaitingAnimation
    {
        private int maxNumberOfDots;
        private int currentDots;
        private MainWindow sender;


        public WaitingAnimation(int maxNumberOfDots, MainWindow sender)
        {
            this.maxNumberOfDots = maxNumberOfDots;
            this.sender = sender;
            currentDots = 0;
        }
    }


    public partial class MainWindow : Window
    {
        ObservableCollection<Person> people = new ObservableCollection<Person>
        {
        };

        public ObservableCollection<Person> Items
        {
            get => people;
        }

        public MainWindow()
        {
            InitializeComponent();
            DataContext = this;
        }
        
        private void AddNewPersonButton_Click(object sender, RoutedEventArgs e)
        {
            int _age = -2;
            try
            {
                _age = int.Parse(ageTextBox.Text);
            }
            catch
            {
                MessageBox.Show("Wiek musi byc liczba calkowita!");
            }
            if(_age > 0)
            people.Add(new Person { Age = _age, Name = nameTextBox.Text, MyImagePath = Person.ImagePath});
            else
            {
                ageTextBox.Text = "";
            }        
        }



        private void Button_Click(object sender, RoutedEventArgs e)
        {
            var filePath = string.Empty;

            OpenFileDialog openFileDialog = new OpenFileDialog();
            openFileDialog.InitialDirectory = "C:\\Users\\micha\\Desktop\\PlatformyProgramistyczne\\Lab01\\Lab01\\Images";
            openFileDialog.Filter = "jpeg files (*.jpg)|*.jpg|All files (*.*)|*.*";
            openFileDialog.FilterIndex = 2;
            openFileDialog.RestoreDirectory = true;

            if (openFileDialog.ShowDialog() == true)
            {
                //Get the path of specified file
                filePath = openFileDialog.FileName;
            }
            if (filePath != "")
            {

                Person.ImagePath = filePath;
                Image.Source = new BitmapImage(new Uri(filePath));
            }
        }



        async Task<string> AccessTheWebAsync()
        {
            // You need to add a reference to System.Net.Http to declare client.  
            using (HttpClient client = new HttpClient())
            {
                // GetStringAsync returns a Task<string>. That means that when you await the  
                // task you'll get a string (urlContents).  
                Task<string> getStringTask = client.GetStringAsync("https://docs.microsoft.com");

                // The await operator suspends AccessTheWebAsync.  
                //  - AccessTheWebAsync can't continue until getStringTask is complete.  
                //  - Meanwhile, control returns to the caller of AccessTheWebAsync.  
                //  - Control resumes here when getStringTask is complete.   
                //  - The await operator then retrieves the string result from getStringTask.  
                string urlContents = await getStringTask;

                // The return statement specifies an integer result.  
                // Any methods that are awaiting AccessTheWebAsync retrieve the length value.  
                return urlContents;
            }
        }

        private async void AddAsyncAdd_Click(object sender, RoutedEventArgs e)
        {
            Task<string> dane = AccessTheWebAsync();
            string _dane = await dane;
            string[] stringSeparators = new string[] {"\"og:image\" content=\""};
            string[] result;

            result = _dane.Split(stringSeparators,
                StringSplitOptions.RemoveEmptyEntries);

            stringSeparators = new string[] {".png"};

        string[] result1 = result[1].Split(stringSeparators,
                StringSplitOptions.RemoveEmptyEntries);

        Person.WebImagePath = result1[0] + ".png";

        var dispatcherTimer = new System.Windows.Threading.DispatcherTimer();
        dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
        dispatcherTimer.Interval = new TimeSpan(0, 0, 1);
        dispatcherTimer.Start();
        }

        private async void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            int _age = 5;
            people.Add(new Person { Age = _age, Name = nameTextBox.Text, MyImagePath = Person.WebImagePath });
        }
    }
}