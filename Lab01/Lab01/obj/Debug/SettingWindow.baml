   M S B A M L   `   `   `  ����@  <Lab01, Version=1.0.0.0, Culture=neutral, PublicKeyToken=nullclr-namespace:Lab01Lab01  5   	       Lab01.SettingWindow   5   	   R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   %localclr-namespace:Lab01     5   	   V RMaterialDesignThemes.Wpf, Version=2.5.1.1345, Culture=neutral, PublicKeyToken=nullFmaterialDesign1http://materialdesigninxaml.net/winfx/xaml/themes  5   	      MaterialDesignBody#���   5   	   $��Regular"�5	   	   $��13(�5
   	   &   System.Windows.Media.TextOptions    TextFormattingMode$  Ideal=�5   	      TextRenderingMode$
 Auto=�5   	     MaterialDesignPaper#���  5   	   $X��Rpack://application:,,,/MaterialDesignThemes.Wpf;component/Resources/Roboto/#Roboto)�5   	    � Title$ SettingWindow��5   	   $	��450��6   $	��800��6,   .��5      � .+�5   
   � -   5         �Name$ GridSetting��5      $
��Left=�6$   $	��399��6?   $��	10,20,0,0q�6L   $	��Top=�6_   $	��284��6w   .+�5      � $
��Left=�6   $��56��6.   $��	10,81,0,0q�6:   $	��Top=�6M   $	��273��6e   .+�5      �� $��City:��6   $
��Left=�6(   $��10,0,0,0q�6C   $	��Top=�6U   $	��101��6m   �� 5      -   5         ��Name$  SettingsCityTextBoxCurrent��5      $
��Left=�6@   $��	10,26,0,0q�6[   $
��Wrap=�6n   $	��Top=�6�   $	��253��6�   $��30��6�   $
��Bold"�6�   5      � 5      $
��Left=�6   $��56��6.   $��
10,142,0,0q�6:   $	��Top=�6N   $	��273��6f   .+�5      �� $��	Language:��6   $
��Left=�6,   $��10,0,0,0q�6G   $	��Top=�6Y   $	��101��6q   �� 5      -   5        $$ SettingsLanguageTextBoxCurrent��5      $
��Left=�6D   $��	10,26,0,0q�6_   $
��Wrap=�6r   $	��Top=�6�   $	��253��6�   $��30��6�   $
��Bold"�6�   5      � 5      $
��Left=�6   $��56��6.   $��
10,203,0,0q�6:   $	��Top=�6N   $	��273��6f   .+�5      �� $
��Url:��6   $
��Left=�6'   $��10,0,0,0q�6B   $	��Top=�6T   $	��101��6l   �� 5      -   5        $ SettingsUrlTextBoxCurrent��5      $
��Left=�6?   $��	10,26,0,0q�6Z   $
��Wrap=�6m   $	��Top=�6�   $	��253��6�   $��30��6�   $
��Bold"�6�   5      �� 5      -   5        $( "SettingsHowManyTimesTextBoxCurrent��5      $
��Left=�6D   $��95��6_   $��
10,294,0,0q�6k   $
��Wrap=�6   $	��Top=�6�   $	��263��6�   $
��Bold"�6�   $��	Underline{�6�   $��22(�6�    �� TextAlignment$ Center=�6�   �� 5       $%��How many times you run this app��6   $
��Left=�6>   $��
10,276,0,0q�6Y   $	��Top=�6m   $	��224��6�   5!      �� 5"   
   -   5         ��Name$ LabelSettingMain��5"      $��CURRENT SETTINGS��6*   $
��Left=�6E   $��	58,21,0,0q�6`   $	��Top=�6s   $	��178��6�   $��16(�6�   $
��Bold"�6�   $��Italic$�6�   �� 5#   
   -   5         ��Name$ ComboLanguageBox��5#      $
��Left=�6-   $��530,181,0,0q�6H   $	��Top=�6]   $	��120��6u   .@�5$      �� $��Polish��6   �� 5%      $��English��6   �� 5&      $��German��6   5'      �� 5(   
   -   5        $ ComboCityBox��5(      $
��Left=�6)   $��530,123,0,0q�6D   $	��Top=�6Y   $	��120��6q   .@�5)      �� $��Wrocław��6   �� 5*      $��Warszawa��6   �� 5+      $
��Cork��6   �� 5,      $��Poznań��6   �� 5-      $��	Oleśnica��6   5.      �� 5/   
   -	   5         ��Name$ EditURLTextBox��5/      $
��Left=�6*   $��23��6E   $��416,249,0,0q�6Q   	 �� TextWrapping$
	 Wrap=�6f   $	��Top=�6z   $	��291��6�   6�   �� 50   
   -
   5        
 ��Name$
 Edit_changesBtn��50      $
��Left=�6*   $��530,315,0,0q�6E   $	��Top=�6Z   $	��120��6r   .��51      �� -   5        $
��Edit��51      $	��103��6$    J� RenderTransformOrigin$ 0.534,-0.742&�6E   $��22��6j   52      54      55      