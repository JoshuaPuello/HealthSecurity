import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSecurityWebSharedModule } from 'app/shared';
import {
    AreaRiesgoComponent,
    AreaRiesgoDetailComponent,
    AreaRiesgoUpdateComponent,
    AreaRiesgoDeletePopupComponent,
    AreaRiesgoDeleteDialogComponent,
    areaRiesgoRoute,
    areaRiesgoPopupRoute
} from './';

const ENTITY_STATES = [...areaRiesgoRoute, ...areaRiesgoPopupRoute];

@NgModule({
    imports: [HealthSecurityWebSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AreaRiesgoComponent,
        AreaRiesgoDetailComponent,
        AreaRiesgoUpdateComponent,
        AreaRiesgoDeleteDialogComponent,
        AreaRiesgoDeletePopupComponent
    ],
    entryComponents: [AreaRiesgoComponent, AreaRiesgoUpdateComponent, AreaRiesgoDeleteDialogComponent, AreaRiesgoDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityWebAreaRiesgoModule {}
