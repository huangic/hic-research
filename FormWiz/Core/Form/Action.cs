using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;

namespace Core.Form
{
    public class Action
    {

        /// <summary>
        /// 讀取前置動作
        /// </summary>
        public ICollection<Core.Form.IActivity> BeforeLoad
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        /// <summary>
        /// 讀取後置動作
        /// </summary>
        public ICollection<Core.Form.IActivity> AfterLoad
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        /// <summary>
        /// 寫入前製動作
        /// </summary>
        public ICollection<Core.Form.IActivity> BeforeSave
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }

        /// <summary>
        /// 寫入後置動作
        /// </summary>
        public ICollection<Core.Form.IActivity> ActionAfterSave
        {
            get
            {
                throw new System.NotImplementedException();
            }
            set
            {
            }
        }


    }
}
