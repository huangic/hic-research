using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Linq.Dynamic;

namespace Core
{
   public class TestObj
    {

        public String Print() { 
        
           

            string[] a = { "1", "2", "3", "4", "5" };



            var f = (from d in a select d);
            f.ExecuteLinq("",null,null);



            return "SHIT!!!";
        }


    }
}
