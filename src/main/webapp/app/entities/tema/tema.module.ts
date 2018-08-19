import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { HealthSecuritySharedModule } from 'app/shared';
import {
    TemaComponent,
    TemaDetailComponent,
    TemaUpdateComponent,
    TemaDeletePopupComponent,
    TemaDeleteDialogComponent,
    temaRoute,
    temaPopupRoute
} from './';

const ENTITY_STATES = [...temaRoute, ...temaPopupRoute];

@NgModule({
    imports: [HealthSecuritySharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [TemaComponent, TemaDetailComponent, TemaUpdateComponent, TemaDeleteDialogComponent, TemaDeletePopupComponent],
    entryComponents: [TemaComponent, TemaUpdateComponent, TemaDeleteDialogComponent, TemaDeletePopupComponent],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class HealthSecurityTemaModule {}
