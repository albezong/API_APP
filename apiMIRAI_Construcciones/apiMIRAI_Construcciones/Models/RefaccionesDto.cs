using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class RefaccionesDto
	{
        public int idRefacciones { get; set; }
        public Nullable<int> idfRevisiones { get; set; }
        public Nullable<int> idfUnidades { get; set; }
        public string nombreRefaccion { get; set; }
        public string descripcion { get; set; }
        public Nullable<int> cantidad { get; set; }
        public string observaciones { get; set; }
        public Nullable<System.DateTime> fecha { get; set; }
        public int numeroReporte { get; set; }

        public RevisionesDto RevisionesDto { get; set; }
        public UnidadesDto UnidadesDto { get; set; }
    }
}