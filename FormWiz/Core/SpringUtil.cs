using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using Spring.Context.Support;

namespace Core
{

    /// <summary>
    /// Get Spring Object Util
    /// </summary>
    /// <history>
    /// 1. Small P ,2011/4/14-上午 11:14, Created
    /// </history>
    public class SpringUtil
    {


       
        public static Object GetBean(String name){
            return ContextRegistry.GetContext().GetObject(name);
        }


        
    }
}
