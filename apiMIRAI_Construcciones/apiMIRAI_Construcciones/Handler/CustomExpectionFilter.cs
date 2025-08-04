using apiMIRAI_Construcciones.Wrapper;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Net;
using System.Threading.Tasks;
using System.Threading;
using System.Web;
using System.Web.Http.Filters;
using NLog;
using System.Net.Http;

namespace apiMIRAI_Construcciones.Handler
{
    public class CustomExpectionFilter : ExceptionFilterAttribute
    {
        private static readonly Logger Log = LogManager.GetCurrentClassLogger();
        public override Task OnExceptionAsync
            (HttpActionExecutedContext context, CancellationToken cancellationToken)
        {
            Log.Error(context.Exception,
                $"Error generado en {context.Exception.Message} " +
                $"en el controller {context.ActionContext.ControllerContext.ControllerDescriptor.ControllerName}");
            //---------------------------------
            //var aqui es una variable de inferiencia
            var respuesta = new ResponseWrapper<HttpResponseOnError>()
            {
                StatusCode = HttpStatusCode.InternalServerError,
                Message = "Ocurrio un error, contacte al administrador para validar los detalles,",
                Data = new HttpResponseOnError()
                {
                    Id = "POS-SYSADM-550", //DsiCodeConst.HAnDLE_ERROR_MESSAGE_ID,
                    Descripcion = "Ocurrio un error, contactate con alguien especializado"
                }
            };

            context.Response = context.Request
                .CreateResponse(HttpStatusCode.InternalServerError, respuesta);
            return Task.CompletedTask;
        }
    }
}