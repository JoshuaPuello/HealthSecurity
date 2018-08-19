import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HealthSecurityPersonaModule } from './persona/persona.module';
import { HealthSecurityUsuarioModule } from './usuario/usuario.module';
import { HealthSecurityReporteModule } from './reporte/reporte.module';
import { HealthSecurityRespuestaModule } from './respuesta/respuesta.module';
import { HealthSecurityAreaRiesgoModule } from './area-riesgo/area-riesgo.module';
import { HealthSecurityTipoRiesgoModule } from './tipo-riesgo/tipo-riesgo.module';
import { HealthSecurityWikiModule } from './wiki/wiki.module';
import { HealthSecurityTemaModule } from './tema/tema.module';
import { HealthSecurityCategoriaModule } from './categoria/categoria.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HealthSecurityPersonaModule,
        HealthSecurityUsuarioModule,
        HealthSecurityReporteModule,
        HealthSecurityRespuestaModule,
        HealthSecurityAreaRiesgoModule,
        HealthSecurityTipoRiesgoModule,
        HealthSecurityWikiModule,
        HealthSecurityTemaModule,
        HealthSecurityCategoriaModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityEntityModule {}
