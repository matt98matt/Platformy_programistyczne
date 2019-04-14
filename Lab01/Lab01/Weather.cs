using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Xml;

namespace Lab01
{
    class Weather
    {
        public string City { set; get; }
        public float Temperature { set; get; }

       
        public static Weather Parse(System.IO.Stream stream)
        {
            XmlTextReader reader = new XmlTextReader(stream);
            Weather result = new Weather()
            {
                City = string.Empty,
                Temperature = float.NaN
            };

            while (reader.Read())
            {
                switch (reader.NodeType)
                {
                    case XmlNodeType.Element:
                        switch (reader.Name)
                        {
                            case "city":
                                result.City = reader.GetAttribute("name");
                                break;
                            case "temperature":
                                result.Temperature =
                                    float.Parse(
                                        reader.GetAttribute("value"),
                                        System.Globalization.CultureInfo.InvariantCulture);
                                break;
                        }
                        break;
                }
            }
            return result;
        }
    }
}
