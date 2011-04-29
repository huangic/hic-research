using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Core.Form
{
      
    public class Column
    {
        public string Id;
        public string Name;

        public string DefaultValue;
        /// <summary>
        /// 對應欄位
        /// </summary>
        public string EntityField;
        /// <summary>
        /// 欄位屬性{xml:raw}
        /// </summary>
        public string FieldType;
        public int Order;
        public int MaxLength;

        public int MinLength;

        public String CssClass;

        public ColumnType ColumnDisplayType;

        public ValidType ColumnValidType;
    }

    public class ColumnType
    {
        String DisplayType;
        int MaxRows;
        int MaxFile;
        int FileSize;
        String RefData;

    }

    public class ValidType {
        String Valid;
        bool Require;
        ICollection<String> RefColumn;
    }

}
