using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.UI;
using System.Web.UI.WebControls;
using Core;

namespace Web
{
    public partial class _Default : System.Web.UI.Page
    {
        protected void Page_Load(object sender, EventArgs e)
        {
            TestObj aaa = (TestObj)SpringUtil.GetBean("ObjTest");
            

            Response.Write(aaa.Print());


           

        }
    }
}
