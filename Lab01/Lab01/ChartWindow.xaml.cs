using System;
using System.Collections.Generic;
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
using LiveCharts;
using LiveCharts.Wpf;


namespace Lab01
{
    public partial class ChartWindow : Window
    {
        public SeriesCollection SeriesCollection { get; set; }
        public ColumnSeries ColumnSeries { get; set; }
        public string[] Labels { get; set; }
        public Func<double, string> Formatter { get; set; }
        
        Table context = new Table();

        public ChartWindow()
        {
            InitializeComponent();
            ChartInitialize();
            DataContext = this;
        }

        

        public void ChartInitialize()
        {
            Model1 db = new Model1();
            var LabelsList = new List<string>();

            SeriesCollection = new SeriesCollection();
            ColumnSeries = new ColumnSeries
            {
                Title = "Series",
                Values = new ChartValues<int>()
            };

            Formatter = value => value.ToString("N");
            var cities = db.Table.Select(x => new { City = x.City });

            var temperatures = db.Table.Select(x => new { Temperature = x.Temperature });



            foreach (var x in cities)
            {
                LabelsList.Add(x.City.ToString());
            }
            foreach (var x in temperatures)
            {
                ColumnSeries.Values.Add(x.Temperature);
            }

            



            SeriesCollection.Add(ColumnSeries);
            Labels = LabelsList.ToArray();
        }

    }
}
