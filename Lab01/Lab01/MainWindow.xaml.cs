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

namespace Lab01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
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
    }
}