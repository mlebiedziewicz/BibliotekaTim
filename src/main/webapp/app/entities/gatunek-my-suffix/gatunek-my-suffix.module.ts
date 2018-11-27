import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliotekaTimSharedModule } from 'app/shared';
import {
    GatunekMySuffixComponent,
    GatunekMySuffixDetailComponent,
    GatunekMySuffixUpdateComponent,
    GatunekMySuffixDeletePopupComponent,
    GatunekMySuffixDeleteDialogComponent,
    gatunekRoute,
    gatunekPopupRoute
} from './';

const ENTITY_STATES = [...gatunekRoute, ...gatunekPopupRoute];

@NgModule({
    imports: [BibliotekaTimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        GatunekMySuffixComponent,
        GatunekMySuffixDetailComponent,
        GatunekMySuffixUpdateComponent,
        GatunekMySuffixDeleteDialogComponent,
        GatunekMySuffixDeletePopupComponent
    ],
    entryComponents: [
        GatunekMySuffixComponent,
        GatunekMySuffixUpdateComponent,
        GatunekMySuffixDeleteDialogComponent,
        GatunekMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimGatunekMySuffixModule {}
