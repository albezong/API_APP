using System;
using System.Collections.Generic;
using System.ComponentModel.DataAnnotations.Schema;
using System.ComponentModel.DataAnnotations;
using System.Linq;
using System.Web;
using Newtonsoft.Json;

namespace APIMIRAI_Construcciones.Models
{
    public class UbicacionesDto
    {
        [Key]
        [DatabaseGenerated(DatabaseGeneratedOption.Identity)]
        public int idUbicaciones { get; set; }
        public string nombre { get; set; }

        [JsonIgnore]
        public ICollection<EquiposDto> EquiposDto { get; set; }
    }
}