using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class QrEquiposDto
	{
        public int idQrEquipos { get; set; }
        public string claveQR { get; set; }
        public Nullable<int> idEquipos { get; set; }

        public EquiposDto EquiposDto { get; set; }
    }
}