// $File: CodeDomHelper.cs $
// This class provides for extending the CodeDom library
// to help coding CodeDom more gracefully with .Net 3.5.
// History:
// $Author: noahwei $
// $Editor: noahwei $
// $Reviwer: noahwei $
// $LastModifyDate: [12/17/2008] $

using System;
using System.CodeDom;
using System.CodeDom.Compiler;
using System.Collections.Generic;
using System.IO;
using System.Reflection;
using System.Linq;
using System.Text;

namespace System
{
    /// <summary>
    /// Extensions Method
    /// </summary>
    public static class ReflectionExtensions
    {
        /// <summary>
        /// An simply way to invode an dynamic genereted method
        /// </summary>
        /// <param name="methodInfo">The method you are call</param>
        /// <param name="parameters">The parameters which should be pass to the method</param>
        /// <returns></returns>
        public static object Invoke(this MethodInfo methodInfo, params object[] parameters)
        {
            return methodInfo.Invoke(null, parameters);
        }
    }
}

namespace System.CodeDom.Extender
{
    /// <summary>
    /// CodeDom Helper
    /// </summary>
    public class CodeDom
    {
        private List<CodeCompileUnit> listCompileUnits = new List<CodeCompileUnit>();
        private List<CodeNamespace> listNamespaces = new List<CodeNamespace>();

        private System.Collections.Specialized.StringCollection listReferencedAssemblies =
            new System.Collections.Specialized.StringCollection() { "mscorlib.dll" };

        /// <summary>
        /// Code language. You can choose C# or VB.
        /// </summary>
        public enum Language
        {
            CSharp,
            VB
        }

        private Language _language;

        /// <summary>
        /// Create an CodeDom class for C# code
        /// I didn't expose the VB CodeDom now since I don't knwon it well.
        /// </summary>
        public CodeDom() : this(Language.CSharp) { }

        private CodeDom(Language language)
        {
            _language = language;
        }

        /// <summary>
        /// This is a static methos which can help you to get
        /// an inctence of the System.CodeDom.Compiler.CodeDomProvider
        /// that is ready for .Net v3.5 code.
        /// </summary>
        /// <param name="provider">Chosse your coding language</param>
        /// <returns></returns>
        public static CodeDomProvider Provider(Language provider)
        {
            var providerOptions =
                new Dictionary<string, string>(); providerOptions.Add("CompilerVersion", "v3.5");

            switch (provider)
            {
                case Language.VB:
                    return new Microsoft.VisualBasic.VBCodeProvider(providerOptions);
                case Language.CSharp:
                default:
                    return new Microsoft.CSharp.CSharpCodeProvider(providerOptions);
            }
        }

        /// <summary>
        /// Same as the following code snippet,
        ///
        /// namespace namespaceName
        /// {
        ///     ...
        /// }
        /// </summary>
        /// <param name="namespaceName">The name of this namespace</param>
        /// <returns></returns>
        public CodeNamespace AddNamespace(string namespaceName)
        {
            CodeNamespace codeNamespace = new CodeNamespace(namespaceName);
            listNamespaces.Add(codeNamespace);

            return codeNamespace;
        }

        /// <summary>
        /// Same as adding an reference assembly
        /// </summary>
        /// <param name="referencedAssembly">The reference assembly name. Such as "System.dll"</param>
        /// <returns></returns>
        public CodeDom AddReference(string referencedAssembly)
        {
            listReferencedAssemblies.Add(referencedAssembly);

            return this;
        }

        /// <summary>
        /// A helper method for creating an Class by CodeDom.
        /// Same as the following code snippet,
        ///
        /// public class ClassName
        /// {
        ///     ...
        /// }
        /// </summary>
        /// <param name="className">The name of the Class you are creating.</param>
        /// <returns></returns>
        public CodeTypeDeclaration Class(string className)
        {
            return new CodeTypeDeclaration(className);
        }

        /// <summary>
        /// A helper method for creating an Method by CodeDom.
        /// Same as the following code snippet,
        ///
        /// public static returnType methodName(paramList)
        /// {
        ///     methodBody
        /// }
        /// </summary>
        /// <param name="returnType">The return type defination</param>
        /// <param name="methodName">The method name</param>
        /// <param name="paramList">The parameter list</param>
        /// <param name="methodBody">The literal code fragment for the method body</param>
        /// <returns></returns>
        public CodeSnippetTypeMember Method(
            string returnType,
            string methodName,
            string paramList,
            string methodBody)
        {
            return Member(
                string.Format("public static {0} {1}({2}) {{ {3} }} ",
                returnType,
                methodName,
                paramList,
                methodBody));
        }

        /// <summary>
        /// A helper method for creating an Method by CodeDom.
        /// Same as the following code snippet,
        ///
        /// public static void methodName(paramList)
        /// {
        ///     methodBody
        /// }
        /// </summary>
        /// <param name="methodName">The method name</param>
        /// <param name="paramList">The parameter list</param>
        /// <param name="methodBody">The literal code for the method body</param>
        /// <returns></returns>
        public CodeSnippetTypeMember Method(
            string methodName,
            string paramList,
            string methodBody)
        {
            return Method("void", methodName, paramList, methodBody);
        }

        /// <summary>
        /// A helper method for creating an Method by CodeDom.
        /// Same as the following code snippet,
        ///
        /// public static void methodName()
        /// {
        ///     methodBody
        /// }
        /// </summary>
        /// <param name="methodName">The method name</param>
        /// <param name="methodBody">The literal code fragment for the method body</param>
        /// <returns></returns>
        public CodeSnippetTypeMember Method(string methodName, string methodBody)
        {
            return Method("void", methodName, "", methodBody);
        }

        /// <summary>
        /// A helper method for creating an Method by CodeDom.
        /// Same as construction of class CodeSnippetTypeMember
        /// </summary>
        /// <param name="methodBody">The literal code fragment for the type member</param>
        /// <returns></returns>
        public CodeSnippetTypeMember Member(string memberBody)
        {
            return new CodeSnippetTypeMember(memberBody);
        }

        /// <summary>
        /// A new CodeCompileUnit to contain the program graph.
        /// </summary>
        public CodeCompileUnit CompileUnit
        {
            get
            {
                // Create a new CodeCompileUnit to contain
                // the program graph.
                CodeCompileUnit compileUnit = new CodeCompileUnit();

                foreach (var ns in listNamespaces)
                    compileUnit.Namespaces.Add(ns);
                return compileUnit;
            }
        }

        /// <summary>
        /// Compile the code we have now to an assembly.
        /// </summary>
        /// <returns></returns>
        public Assembly Compile()
        {
            return Compile(null);
        }

        /// <summary>
        /// Compile the code we have now to an assembly.
        /// </summary>
        /// <param name="assemblyPath">The path where the assembly file drop to</param>
        /// <returns></returns>
        public Assembly Compile(string assemblyPath)
        {
            CompilerParameters options = new CompilerParameters();
            options.IncludeDebugInformation = false;
            options.GenerateExecutable = false;
            options.GenerateInMemory = (assemblyPath == null);

            foreach (string refAsm in listReferencedAssemblies)
                options.ReferencedAssemblies.Add(refAsm);
            //Add assembly which is referanced by current assembly also.
            List<string[]> refAsmRefs = new List<string[]>();
            try
            {
                refAsmRefs.AddRange(
                    from a in Assembly.GetEntryAssembly().GetReferencedAssemblies()
                    select new string[] { a.Name, a.FullName });
                refAsmRefs.AddRange(
                    from a in Assembly.GetCallingAssembly().GetReferencedAssemblies()
                    select new string[] { a.Name, a.FullName });
                refAsmRefs.AddRange(
                    from a in Assembly.GetExecutingAssembly().GetReferencedAssemblies()
                    select new string[] { a.Name, a.FullName });

            }
            catch { }
            foreach (var refAsmRef in refAsmRefs)
            {
                string asmNameSimp = refAsmRef[0];
                string asmFullName = refAsmRef[1];
                if (!(options.ReferencedAssemblies.Contains(asmFullName)
                    || options.ReferencedAssemblies.Contains(asmNameSimp)))
                {
                    options.ReferencedAssemblies.Add(asmFullName);
                }
            }
            if (assemblyPath != null)
                options.OutputAssembly = assemblyPath.Replace('\\', '/');

            CodeDomProvider codeProvider = Provider(_language);
            CompilerResults results =
               codeProvider.CompileAssemblyFromDom(options, CompileUnit);
            codeProvider.Dispose();
            if (results.Errors.Count == 0)
                return results.CompiledAssembly;

            // Process compilation errors
            System.Diagnostics.Trace.WriteLine("Compilation Errors:");
            foreach (string outpt in results.Output)
                System.Diagnostics.Trace.WriteLine(outpt);
            foreach (CompilerError err in results.Errors)
                System.Diagnostics.Trace.WriteLine(err.ToString());

            return null;
        }

        /// <summary>
        /// Generate all code to a string.
        /// </summary>
        /// <returns></returns>
        public string GenerateCode()
        {
            StringBuilder sb = new StringBuilder();
            TextWriter tw = new IndentedTextWriter(new StringWriter(sb));

            CodeDomProvider codeProvider = Provider(_language);
            codeProvider.GenerateCodeFromCompileUnit(
                CompileUnit,
                tw,
                new CodeGeneratorOptions());
            codeProvider.Dispose();

            tw.Close();
            return sb.ToString();
        }
    }

    /// <summary>
    /// Converts from "object" general type to a concrete type
    /// </summary>
    /// <remarks>
    /// The class is working with .Net 2.0 and above
    /// </remarks>
    public class Converter
    {
        /// <summary>
        /// Returns True if the type can get Null as a value
        /// (is a reference type and not a value one)
        /// </summary>
        public static bool IsNullable(Type t)
        {
            if (!t.IsGenericType) return false;
            Type g = t.GetGenericTypeDefinition();
            return (g.Equals(typeof(Nullable<>)));
        }

        /// <summary>
        /// Returns a real type of a first generic argument
        /// </summary>
        private static Type UnderlyingTypeOf(Type t)
        {
            return t.GetGenericArguments()[0];
        }

        /// <summary>
        /// Converter
        /// </summary>
        public static T To<T>(object value, T defaultValue)
        {
            if (value == DBNull.Value) return defaultValue;
            Type t = typeof(T);
            if (IsNullable(t))
            {
                if (value == null) return default(T);
                t = UnderlyingTypeOf(t);
            }
            else
            {
                if ((value == null) && (t.IsValueType))
                {
                    return defaultValue;
                }
            }
            return (T)Convert.ChangeType(value, t);
        }
    }

    /// <summary>
    /// The extensions class for the CodeDom Helper
    /// </summary>
    public static class CodeDomeExtensions
    {
        /// <summary>
        /// Add a class into this namaspace
        /// </summary>
        /// <param name="codeType">Type Declaration</param>
        /// <returns></returns>
        public static CodeNamespace AddClass(
            this CodeNamespace codeNamespace,
            CodeTypeDeclaration codeType)
        {
            codeNamespace.Types.Add(codeType);
            return codeNamespace;
        }

        /// <summary>
        /// Add a member into this class
        /// </summary>
        /// <param name="memberBody"></param>
        /// <returns></returns>
        public static CodeTypeDeclaration AddMember(
            this CodeTypeDeclaration classCode,
            CodeSnippetTypeMember memberBody)
        {
            classCode.Members.Add(memberBody);
            return classCode;
        }

        /// <summary>
        /// Add an imported namespace
        /// </summary>
        /// <param name="namespaceName"></param>
        /// <returns></returns>
        public static CodeNamespace Imports(
            this CodeNamespace codeNamespace,
            string namespaceName)
        {
            codeNamespace.Imports.Add(
                new CodeNamespaceImport(namespaceName));
            return codeNamespace;
        }
    }
}