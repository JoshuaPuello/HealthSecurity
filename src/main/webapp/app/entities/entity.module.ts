import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { HealthSecurityPersonaModule } from './persona/persona.module';
import { HealthSecurityUsuarioModule } from './usuario/usuario.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    // prettier-ignore
    imports: [
        HealthSecurityPersonaModule,
        HealthSecurityUsuarioModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityEntityModule {}
