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
using System.Windows.Shapes;

namespace Lab01
{
    /// <summary>
    /// Logika interakcji dla klasy SettingWindow.xaml
    /// </summary>
    public partial class SettingWindow : Window
    {
        public SettingWindow()
        {
            InitializeComponent();
            DataContext = this;

            SettingsCityTextBoxCurrent.Text = Properties.Settings.Default.City;
            SettingsHowManyTimesTextBoxCurrent.Text = Properties.Settings.Default.HowManyTimesItIsRunning.ToString();
            SettingsLanguageTextBoxCurrent.Text = Properties.Settings.Default.Language;
            SettingsUrlTextBoxCurrent.Text = Properties.Settings.Default.Url;
        }
        private void ComboLanguageBox_Loaded(object sender, RoutedEventArgs e)
        {
            ComboLanguageBox.Text = SettingsLanguageTextBoxCurrent.Text;
        }

        private void ComboLanguageBox_DropDownClosed(object sender, EventArgs e)
        {
            SettingsLanguageTextBoxCurrent.Text = ComboLanguageBox.Text;
        }

        private void ComboCityBox_Loaded(object sender, RoutedEventArgs e)
        {
            ComboCityBox.Text = SettingsCityTextBoxCurrent.Text;
        }

        private void ComboCityBox_DropDownClosed(object sender, EventArgs e)
        {
            SettingsCityTextBoxCurrent.Text = ComboCityBox.Text;
        }

        private void EditURLTextBox_Loaded(object sender, RoutedEventArgs e)
        {
            EditURLTextBox.Text = SettingsUrlTextBoxCurrent.Text;
        }

        private void EditURLTextBox_TextChanged(object sender, TextChangedEventArgs e)
        {
            SettingsUrlTextBoxCurrent.Text = EditURLTextBox.Text;
        }

        private void Button_Click(object sender, RoutedEventArgs e)
        {
            Properties.Settings.Default.City = SettingsCityTextBoxCurrent.Text;
            Properties.Settings.Default.Language = SettingsLanguageTextBoxCurrent.Text;
            Properties.Settings.Default.Url = SettingsUrlTextBoxCurrent.Text;

            MessageBox.Show("Edited succesful");
        }
    }
}
