using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Testing.NUnit;
using NUnit.Framework;
using Core;
using Core.Form;
using System.Xml.Serialization;
using Newtonsoft.Json;
using log4net.Core;
using Common.Logging;
using System.Xml;


namespace Test.Core
{
    [TestFixture]
    public class CoreTest:AbstractDependencyInjectionSpringContextTests
    {
        protected override string[] ConfigLocations
        {
            get
            {
                return new String[] { "assembly://Core/Core.SpringConfig/Spring.xml" };
            }
        }

        [Test]
        public void Test(){
            TestObj aaa = (TestObj)SpringUtil.GetBean("ObjTest");

            Assert.AreEqual(aaa.Print(), "SHIT!!!");
        }


        [Test]
        public void TestForm() {
             ILog logger= LogManager.GetLogger(typeof(CoreTest));
            
            //make mock form
            Console.WriteLine("Test");
            Form f = new Form();

            f.Description = "Mock Form Description";
            f.Subject = "MockForm";
            f.FormStyle = "2Column.xsl";


            String s=JsonConvert.SerializeObject(f);

            logger.Debug("SHIT");

            logger.Debug(s);

            Console.WriteLine(s);


            String xmls = @"{""?xml"": {""@version"": ""1.0"",""@standalone"": ""no""},""FormDefine"":"+s+"}";
           
            
            //xmls=String.Format(xmls, s);

            logger.Debug(xmls);


            XmlDocument xdoc = JsonConvert.DeserializeXmlNode(xmls);

            logger.Debug(xdoc.OuterXml);

            String xml_json=JsonConvert.SerializeXmlNode(xdoc.FirstChild);

            f=(Form) JsonConvert.DeserializeObject(xml_json);

            logger.Debug(xml_json);

        }
    }
}
