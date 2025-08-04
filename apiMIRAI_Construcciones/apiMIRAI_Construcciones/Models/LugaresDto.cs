using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class LugaresDto
	{
        public int idLugares { get; set; }
        public string nombreLugar { get; set; }
        public Nullable<int> idfEquipos { get; set; }

        public EquiposDto EquiposDto { get; set; }
    }
}