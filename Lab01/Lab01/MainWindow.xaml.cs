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

namespace Lab01
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        ObservableCollection<Person> people = new ObservableCollection<Person>
        {
            new Person { Name = "P1", Age = 1, MyImagePath = ""},
            new Person { Name = "P2", Age = 2, MyImagePath ="" }
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

        private void AddPhoto1_Click(object sender, RoutedEventArgs e)
        {
            Person.ImagePath = "D:/Pobrane/Lab01/Lab01/Images/pilka1.jpg";
            Image.Source = new BitmapImage(new Uri(Person.ImagePath));
        }

        private void AddPhoto2_Click(object sender, RoutedEventArgs e)
        {
            Person.ImagePath = "D:/Pobrane/Lab01/Lab01/Images/pilka2.jpg";
            Image.Source = new BitmapImage(new Uri(Person.ImagePath));
        }

        private void AddPhoto3_Click(object sender, RoutedEventArgs e)
        {
            Person.ImagePath = "D:/Pobrane/Lab01/Lab01/Images/pilka3.jpg";
            Image.Source = new BitmapImage(new Uri(Person.ImagePath));
        }
    }
}