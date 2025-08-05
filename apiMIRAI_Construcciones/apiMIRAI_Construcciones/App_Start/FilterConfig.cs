using System.Web;
using apiMIRAI_Construcciones.Filters;
using System.Web.Mvc;

namespace apiMIRAI_Construcciones
{
    public class FilterConfig
    {
        public static void RegisterGlobalFilters(GlobalFilterCollection filters)
        {
            filters.Add(new HandleErrorAttribute());
            filters.Add(new ExceptionFilterAttribute());
        }
    }
}
