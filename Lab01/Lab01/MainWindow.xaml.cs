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
using System.ComponentModel;
using System.Drawing.Printing;
using System.Threading;

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
        BackgroundWorker worker = new BackgroundWorker();
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

            worker.WorkerReportsProgress = true;
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += Worker_DoWork;
            worker.ProgressChanged += Worker_ProgressChanged;
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

        public void AddPerson(Person person)
        {
            Application.Current.Dispatcher.Invoke(() => { Items.Add(person); });
        }

        async Task<string> AccessTheWebAsync()
        {
            // You need to add a reference to System.Net.Http to declare client.  
            using (HttpClient client = new HttpClient())
            {
                // GetStringAsync returns a Task<string>. That means that when you await the  
                // task you'll get a string (urlContents).  
                //Task<string> getStringTask = client.GetStringAsync("https://docs.microsoft.com");
                Task<string> getStringTask = client.GetStringAsync("https://www.adobe.com/pl/creativecloud.html?gclid=EAIaIQobChMImbbUqcOR4QIV1cAYCh0thgIiEAAYASAAEgIjVfD_BwE&sdid=8JD95K3R&mv=search&skwcid=AL!3085!3!281619997375!e!!g!!adobe&ef_id=EAIaIQobChMImbbUqcOR4QIV1cAYCh0thgIiEAAYASAAEgIjVfD_BwE:G:s&s_kwcid=AL!3085!3!281619997375!e!!g!!adobe");
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
            //string[] stringSeparators = new string[] {"\"og:image\" content=\""};
            string[] stringSeparators = new string[] {"src=\"/content/",".png"};
            string[] result;

            result = _dane.Split(stringSeparators,
                StringSplitOptions.RemoveEmptyEntries);

          //  stringSeparators = new string[] {".png"};

        //string[] result1 = result[1].Split(stringSeparators,
       //         StringSplitOptions.RemoveEmptyEntries);
       Person.WebImageAllPath = "https://www.adobe.com/content/" + result[3] + ".png" + " https://www.adobe.com/content/" + result[5] + ".png" + " https://www.adobe.com/content/" + result[7] + ".png" + " https://www.adobe.com/content/" + result[9] + ".png" + " https://www.adobe.com/content/" + result[11] + ".png";
       // Person.WebImagePath = result1[0] + ".png";
       
        var dispatcherTimer = new System.Windows.Threading.DispatcherTimer();
        dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
        dispatcherTimer.Interval = new TimeSpan(0, 0, 1);
        dispatcherTimer.Start();
        }

        private async void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            int _age = 5;
            string[] result;
            result = Person.WebImageAllPath.Split(new string[] {" "},
                StringSplitOptions.RemoveEmptyEntries);
            Random rnd = new Random();
        Person.WebImagePath = result[rnd.Next(0,5)];
            //people.Add(new Person { Age = _age, Name = nameTextBox.Text, MyImagePath = Person.WebImagePath });
            //people.Add(new Person { Age = (Person.WebImagePath.Length - rnd.Next(0,60)), Name = nameTextBox.Text, MyImagePath = Person.WebImagePath });
        }

        private async void WeatherDataButton_Click(object sender, RoutedEventArgs e)
        {
            string responseXML = await WeatherConnection.LoadDataAsync("Wrocław");
            WeatherDataEntry result;

            using (MemoryStream stream = new MemoryStream(Encoding.UTF8.GetBytes(responseXML)))
            {
                result = ParseWheater_LINQ.Parse(stream);
                Items.Add(new Person()
                {
                    Name = result.City,
                    Age = (int)Math.Round(result.Temperature)
                });
            }

            if (worker.IsBusy != true)
                worker.RunWorkerAsync();
        }

        private void Worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            weatherDataProgressBar.Value = e.ProgressPercentage;
            weatherDataTextBlock.Text = e.UserState as string;
        }

        private void Worker_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;

            List<string> cities = new List<string> {
                "London", "Warsaw", "Paris", "London", "Warsaw", "Poznań", "Łódź", "Gdańsk", "Cork" };
            for (int i = 1; i <= cities.Count; i++)
            {
                string city = cities[i - 1];

                if (worker.CancellationPending == true)
                {
                    worker.ReportProgress(0, "Cancelled");
                    e.Cancel = true;
                    return;
                }
                else
                {
                    worker.ReportProgress(
                        (int)Math.Round((float)i * 100.0 / (float)cities.Count),
                        "Loading " + city + "...");
                    string responseXML = WeatherConnection.LoadDataAsync(city).Result;
                    WeatherDataEntry result;

                    using (MemoryStream stream = new MemoryStream(Encoding.UTF8.GetBytes(responseXML)))
                    {
                        result = ParseWheater_LINQ.Parse(stream);
                        Person ppl = new Person();
                        ppl.Name = result.City;
                        ppl.Age = (int)Math.Round(result.Temperature);

                        AddPerson(ppl);
                       
                    }
                    Thread.Sleep(2000);
                }
            }
            worker.ReportProgress(100, "Done");
        }

        private void Button_Click_1(object sender, RoutedEventArgs e)
        {
            if (worker.WorkerSupportsCancellation == true)
            {
                weatherDataTextBlock.Text = "Cancelling...";
                worker.CancelAsync();
            }
        }

    }
}