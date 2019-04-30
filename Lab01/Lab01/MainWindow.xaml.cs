using System;
using System.Collections.Generic;
using System.ComponentModel;
using System.Data.Entity;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading;
using System.Timers;
using System.Windows;
using System.Windows.Data;
using System.Windows.Threading;
using System.Collections.ObjectModel;
using System.Xml;

namespace Lab01
{
    public partial class MainWindow : Window
    {
       
        Model1 db = new Model1();
        CollectionViewSource personEntryViewSource;
        CollectionViewSource wheaterEntryViewSource;
       
        BackgroundWorker worker = new BackgroundWorker();
        DispatcherTimer dispatcherTimer = new DispatcherTimer();
        #region Constructors
        public MainWindow()
        {
               
             





            InitializeComponent();

            this.DataContext = this;

            worker.WorkerReportsProgress = true;
            worker.WorkerSupportsCancellation = true;
            worker.DoWork += Worker_DoWork;
            worker.ProgressChanged += Worker_ProgressChanged;

            dispatcherTimer.Tick += new EventHandler(dispatcherTimer_Tick);
            dispatcherTimer.Interval = new TimeSpan(0, 0, 15);

            personEntryViewSource = (CollectionViewSource)this.FindResource("personEntryViewSource");
            wheaterEntryViewSource = (CollectionViewSource)this.FindResource("wheaterEntryViewSource");
            
           

            string[] settings = File.ReadAllLines(@"settings.txt"); // lab1 -> bin -> debug
            Properties.Settings.Default.City = settings[0];
            Properties.Settings.Default.Language = settings[1];
            Properties.Settings.Default.Url = settings[2];
            Properties.Settings.Default.HowManyTimesItIsRunning = Convert.ToInt32(settings[3]);
            Properties.Settings.Default.HowManyTimesItIsRunning++;

            
        }

        private  void Window_Loaded(object sender, RoutedEventArgs e)
        {
           
            db.Test.Local.Concat(db.Test.ToList());
            db.Table.Local.Concat(db.Table.ToList());
            personEntryViewSource.Source = db.Test.Local;
            wheaterEntryViewSource.Source = db.Table.Local;
           

            GetWeatherFromWebSometimes();
            dispatcherTimer.Start();
        }

        private void Window_Closed(object sender, EventArgs e)
        {
            string path = @"settings.txt";
            StreamWriter sw = new StreamWriter(path);
            if (!File.Exists(path))
            {
                sw = File.CreateText(path);
            }
            sw.Write($"{Properties.Settings.Default.City}");
            sw.WriteLine();
            sw.Write($"{Properties.Settings.Default.Language}");
            sw.WriteLine();
            sw.Write($"{Properties.Settings.Default.Url}");
            sw.WriteLine();
            sw.Write($"{Properties.Settings.Default.HowManyTimesItIsRunning}");
            sw.Close();
        }

        private void OpenSettingsBtn_Click(object sender, RoutedEventArgs e)
        {
            SettingWindow win2 = new SettingWindow();
            win2.Show();
        }
        #endregion 

        private void OpenChartsWindowBtn_Click(object sender, RoutedEventArgs e)
        {
            ChartWindow win3 = new ChartWindow();
            win3.Show();
        }

        #region WorkerFunctions
        private void Worker_DoWork(object sender, DoWorkEventArgs e)
        {
            BackgroundWorker worker = sender as BackgroundWorker;

            List<string> cities = new List<string> {
                "London", "Warsaw", "Paris", "Wrocław", "Poznań", "Łódź", "Gdańsk", "Cork" };
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
                    Weather result;

                    using (MemoryStream stream = new MemoryStream(Encoding.UTF8.GetBytes(responseXML)))
                    {
                        result = Weather.Parse(stream);
                        int _id = db.Table.Local.Count;
                        var wheaterToCreate = new Table()
                        {
                            Id = _id + 1,
                            City = result.City,
                            Temperature = (int)result.Temperature
                        };
                        AddWeather(wheaterToCreate);
                    }
                    Thread.Sleep(2000);
                }
            }
            worker.ReportProgress(100, "Done");
        }

        private void Worker_ProgressChanged(object sender, ProgressChangedEventArgs e)
        {
            weatherDataProgressBar.Value = e.ProgressPercentage;
            weatherDataTextBlock.Text = e.UserState as string;
        }

        #endregion

        #region PersonFunctions
        private void BtnAddPerson_Click(object sender, RoutedEventArgs e)
        {
            int _age = -2;
            int _id = -2;
            try
            {
                _age = int.Parse(ageTextBox.Text);
                _id = int.Parse(idTextBox.Text);
            }
            catch
            {
                MessageBox.Show("Wiek oraz Id musi byc liczba calkowita!");
            }

            if (_age > 0 && _id >= 0)
            {
                var personToCreate = new Test()
                {
                    Id = _id,
                    Name = nameTextBox.Text,
                    Age = _age
                };
                db.Test.Local.Add(personToCreate);
                try
                {
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    db.Test.Local.Remove(personToCreate);
                    MessageBox.Show("Id powtarza się! Wprowadź inną cyfrę.");
                }
            }
            else
            {
                ageTextBox.Text = "";
                idTextBox.Text = "";
            }
        }

        private async void DeleteAllPersonsBtn_Click(object sender, RoutedEventArgs e)
        {
            var persons = await db.Test.ToListAsync();

            foreach (var person in persons)
            {
                db.Test.Local.Remove(person);
            }
            db.SaveChanges();
        }

        private async void DeletePersonBtn_Click(object sender, RoutedEventArgs e)
        {
            int id;
            try
            {
                id = Convert.ToInt32(idPersonToDeleteTextBox.Text);
            }
            catch
            {
                MessageBox.Show("Only integers");
                idPersonToDeleteTextBox.Text = "Write ID person to delete";
                deletePersonBtn.IsEnabled = false;
                return;
            }


            var personToDelete = await db.Test.FirstOrDefaultAsync(x => x.Id == id);

            if (personToDelete != null)
            {
                db.Test.Local.Remove(personToDelete);
                db.SaveChanges();
                return;
            }
            MessageBox.Show("Smth was wrong. Are u wrote good ID ??");
        }

        private void IdPersonToDelete_GotFocus(object sender, RoutedEventArgs e)
        {
            idPersonToDeleteTextBox.Text = "";
            deletePersonBtn.IsEnabled = true;

        }
        #endregion

        #region WeatherFunctions
        private void BtnAddWheater_Click(object sender, RoutedEventArgs e)
        {
            int _temperature = -2;
            try
            {
                _temperature = int.Parse(temperatureTextBox.Text);
            }
            catch
            {
                MessageBox.Show("Temperature must be integer");
                temperatureTextBox.Text = "";
                cityTextBox.Text = "";
                return;
            }

            int _id = db.Table.Local.Count;
            var wheaterToCreate = new Table()
            {
                Id = _id + 1,
                City = cityTextBox.Text,
                Temperature = _temperature
            };
                db.Table.Local.Add(wheaterToCreate);
                try
                {
                    db.SaveChanges();
                }
                catch (Exception ex)
                {
                    db.Table.Local.Remove(wheaterToCreate);
                    MessageBox.Show("Cos zle z pogodynka");
                }
        }

        private void BtnAddAllWheater_Click(object sender, RoutedEventArgs e)
        {
            if (worker.IsBusy != true)
                worker.RunWorkerAsync();
        }

        public void AddWeather(Table weather)
        {
            Application.Current.Dispatcher.Invoke(() => {
                if(weather.City != "" && weather.Temperature != -2147483648)
                {
                    db.Table.Local.Add(weather);
                    try
                    {
                        db.SaveChanges();
                    }
                    catch (Exception ex)
                    {
                        db.Table.Local.Remove(weather);
                        MessageBox.Show("Error(MainWindow.cs: 148):\n"+ ex.Message);
                    }
                }
                else
                {
                    MessageBox.Show("Wrong name of city!");
                    searchTextBox.Text = "";
                }
                
            });
        }

        private void CancelLoadWheater(object sender, RoutedEventArgs e)
        {
            if (worker.WorkerSupportsCancellation == true)
            {
                weatherDataTextBlock.Text = "Cancelling...";
                worker.CancelAsync();
            }
        }

        private async void SearchClick(object sender, RoutedEventArgs e)
        {
            string responseXML = await WeatherConnection.LoadDataAsync(searchTextBox.Text);
            Weather result;

            using (MemoryStream stream = new MemoryStream(Encoding.UTF8.GetBytes(responseXML)))
            {
                result = Weather.Parse(stream);
                int _id = db.Table.Local.Count;
                var wheaterToCreate = new Table()
                {
                    Id = _id + 1,
                    City = result.City,
                    Temperature = (int)result.Temperature
                };
                AddWeather(wheaterToCreate);
            }
        }

        private async void DeleteAllWeatersBtn_Click(object sender, RoutedEventArgs e)
        {
            var weaters = await db.Table.ToListAsync();

            foreach (var weater in weaters)
            {
                db.Table.Local.Remove(weater);
            }
            db.SaveChanges();
        }

        private void IdWeaterToDeleteTextBox_GotFocus(object sender, RoutedEventArgs e)
        {
            idWeaterToDeleteTextBox.Text = "";
            DeleteWeaterBtn.IsEnabled = true;
        }

        private async void DeleteWeaterBtn_Click(object sender, RoutedEventArgs e)
        {
            int id;
            try
            {
                 id = Convert.ToInt32(idWeaterToDeleteTextBox.Text);
            }
            catch
            {
                MessageBox.Show("Only integers");
                idWeaterToDeleteTextBox.Text = "Write ID weater to delete";
                DeleteWeaterBtn.IsEnabled = false;
                return;
            }
            
            var weaterToDelete = await db.Table.FirstOrDefaultAsync(x => x.Id == id);

            if (weaterToDelete != null)
            {
                db.Table.Local.Remove(weaterToDelete);
                db.SaveChanges();
                return;
            }
            MessageBox.Show("Smth was wrong. Are u wrote good ID ??");
        }
        #endregion


        private void dispatcherTimer_Tick(object sender, EventArgs e)
        {
            GetWeatherFromWebSometimes();
        }

        public  async void GetWeatherFromWebSometimes()
        {
            DateTime date =  DateTime.Now;
            string dateHours = date.ToString("HH:mm:ss");
            CheckWeaterWelcomeTextBlock.Text = "Last update in app: " + dateHours;

            string responseXML = "";
            responseXML =  await WeatherConnection.LoadDataAsync(Properties.Settings.Default.City);
            using (MemoryStream stream = new MemoryStream(Encoding.UTF8.GetBytes(responseXML)))
            {
                XmlTextReader reader = new XmlTextReader(stream);

                while (reader.Read())
                {
                    switch(reader.NodeType)
                    {
                        case XmlNodeType.Element:
                            switch (reader.Name)
                            {
                                case "city":
                                    CheckWeaterNameCityTextBlock.Text = reader.GetAttribute("name");
                                    while (reader.Read())
                                    {
                                        switch(reader.NodeType)
                                        {
                                            case XmlNodeType.Element:
                                                switch(reader.Name)
                                                {
                                                    case "sun":
                                                        CheckWeaterSunriseValueTextBlock.Text = reader.GetAttribute("rise");
                                                        CheckWeaterSunsetValueTextBlock.Text = reader.GetAttribute("set");
                                                        break;
                                                    case "temperature":
                                                        CheckWeaterTemperatureMinTextBlock.Text = "Min: " + reader.GetAttribute("min");
                                                        CheckWeaterTemperatureMaxTextBlock.Text = "Max: " + reader.GetAttribute("max");
                                                        break;
                                                    case "speed":
                                                        CheckWeaterWindSpeedNameTextBlock.Text =  reader.GetAttribute("name");
                                                        break;
                                                    case "direction":
                                                        CheckWeaterWindDirectionNameTextBlock.Text ="Directory: " + reader.GetAttribute("name");
                                                        break;
                                                    case "clouds":
                                                        CheckCloudsNameTextBlock.Text = reader.GetAttribute("name");
                                                        break;
                                                    case "weather":
                                                        CheckWeaterValueTextBlock.Text = reader.GetAttribute("value");
                                                        break;
                                                    case "lastupdate":
                                                        CheckWeaterLastUpdateTextBlock.Text = "Last update: " + reader.GetAttribute("value");
                                                        break;
                                                }
                                                break;
                                            case XmlNodeType.Text:
                                                CheckWeaterCityCountry.Text = reader.Value;
                                                break;
                                        }
                                    }
                                    break;
                            }
                            break;
                    }
                }
            }        
        }

        

        

       
    }
}