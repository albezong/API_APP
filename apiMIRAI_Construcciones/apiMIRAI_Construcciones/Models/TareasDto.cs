using apiMIRAI_Construcciones.Data;
using System;
using System.Collections.Generic;
using System.Linq;
using System.Web;

namespace apiMIRAI_Construcciones.Models
{
	public class TareasDto
	{
        public int idTareas { get; set; }
        public Nullable<int> idTiposMantenimientos { get; set; }
        public string descripcion { get; set; }

        public ICollection<RecordatoriosDto> RecordatoriosDto { get; set; }
        public TiposMantenimientosDto TiposMantenimientosDto { get; set; }
    }
}