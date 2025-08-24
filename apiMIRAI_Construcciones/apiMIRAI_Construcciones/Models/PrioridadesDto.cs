using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using APIMIRAI_Construcciones.Data;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class PrioridadesDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idPrioridades { get; set; }
        public string nombre { get; set; }

        [JsonIgnore]
        public ICollection<DetallesPreventivosDto> DetallesPreventivosDto { get; set; }
        [JsonIgnore]
        public ICollection<RecordatoriosDto> RecordatoriosDto { get; set; }
        [JsonIgnore]
        public ICollection<RefaccionesDto> RefaccionesDto { get; set; }
    }
}