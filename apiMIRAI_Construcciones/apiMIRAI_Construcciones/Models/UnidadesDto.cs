using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class UnidadesDto
	{
        public int idUnidades { get; set; }
        public string nombre { get; set; }

        public ICollection<Equipos> EquiposDto { get; set; }
        public ICollection<Refacciones> RefaccionesDto { get; set; }
    }
}