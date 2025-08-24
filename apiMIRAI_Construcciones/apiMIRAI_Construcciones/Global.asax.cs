using Newtonsoft.Json.Serialization;
using NLog;
using NLog.Config;
using NLog.Targets;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;
using System.Web.Http;
using System.Web.Routing;

namespace APIMIRAI_Construcciones
{
    public class WebApiApplication : System.Web.HttpApplication
    {
        private static readonly Logger logger = LogManager.GetCurrentClassLogger();
        protected void Application_Start()
        {
            GlobalConfiguration.Configure(WebApiConfig.Register);
            //SwaggerConfig.Register();
            //Configuracion de NLog programatica
            var config = new LoggingConfiguration();
            //Crear un destino de archivo
            var fileTarget = new FileTarget("logfile")
            {
                FileName = "${basedir}/logs/logfile.txt",
                Layout = "${longdate} ${level:uppercase = true} ${message} ${excepcion}"
            };

            //Añadimos los destinos a la configuracion
            config.AddTarget(fileTarget);
            //Crear y añadir reglas
            var fileRule = new
                LoggingRule("*", NLog.LogLevel.Info, fileTarget);
            config.LoggingRules.Add(fileRule);
            //Aplicamos configurar NLog con la configuracion
            LogManager.Configuration = config;

            //Configuracion de salida a peticiones
            HttpConfiguration http = GlobalConfiguration.Configuration;
            http
                .Formatters
                .JsonFormatter
                .SerializerSettings
                .ContractResolver = new JsonLowerCaseResolver()
                {
                    NamingStrategy = new SnakeCaseNamingStrategy()
                };
        }
        public class JsonLowerCaseResolver : DefaultContractResolver
        {
            protected override string
                ResolvePropertyName(string propertyName)
            {
                return propertyName.ToLower();
            }
        }
    }
}
