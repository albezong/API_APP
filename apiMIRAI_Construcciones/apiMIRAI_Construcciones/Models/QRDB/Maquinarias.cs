using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models.QRDB
{
	public class Maquinarias
    {
        public int id { get; set; }
        public string nombre { get; set; }
        public string descripcion { get; set; }
        public Nullable<System.Guid> qr_uuid { get; set; }
        public string url_imagen { get; set; }
    }
}