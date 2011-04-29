// $File: DynamicLinq.cs $
// This class provides for extending the
// LINQ library to support Dynamic LINQ.
// History:
// $Author: noahwei $
// $Editor: noahwei $
// $Reviwer: noahwei $
// $LastModifyDate: [12/19/2008] $
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.CodeDom.Extender;

namespace System.Linq.Dynamic
{
    public static class IEnumerableExtender
    {
        public static IEnumerable<object> ExecuteLinq<T>(
            this IEnumerable<T> enumerableObject,
            string linqString,
            string parmDefinition,
            params object[] parameters)
        {
            var c = new System.CodeDom.Extender.CodeDom();
            c.AddReference("mscorlib.dll")
             .AddReference("System.Core.dll")
             .AddReference("System.Data.dll")
             .AddReference("System.Data.Linq.dll")
             .AddReference("System.Xml.dll")
             .AddReference("System.Xml.Linq.dll")
                 .AddNamespace("DynamicLinq")
                 .Imports("System.Collections.Generic")
                 .Imports("System.Linq")
                 .Imports("System.Data.Linq")
                 .Imports("System.Xml.Linq")
                     .AddClass(c.Class("DynamicLinqHelper")
                         .AddMember(c.Method("IEnumerable<object>",
                         "ExecuteLinq",
                         parmDefinition,
                         linqString)));
            var method = c.Compile()
                .GetType("DynamicLinq.DynamicLinqHelper")
                .GetMethod("ExecuteLinq");
            return method.Invoke(parameters) as IEnumerable<object>;
        }
    }
}