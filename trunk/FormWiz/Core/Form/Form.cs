using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Xml.Serialization;

namespace Core.Form
{
    /// <remarks>表單定義抽象類別</remarks>
    public class Form
    {



        /// <summary>
        /// 說明
        /// </summary>
        public String Description
        {
            get;
            set;       
                
        }

        /// <summary>
        /// 表單名稱
        /// </summary>
        public String Subject
        {
            get;set;       
                
         
        }

        /// <summary>
        /// 表單樣式
        /// </summary>
        public String FormStyle
        {
            get;
            set;       
                
        }

        /// <summary>
        /// Entity 定義
        /// </summary>
        public Entity Entity
        {
            get;
            set;       
                
        }

        /// <summary>
        /// 欄位定義
        /// </summary>
        public ICollection<Core.Form.Column> Columns
        {
            get;
            set;       
                
        }

      
    }
}
