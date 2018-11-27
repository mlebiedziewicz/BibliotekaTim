import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliotekaTimSharedModule } from 'app/shared';
import {
    KsiazkaMySuffixComponent,
    KsiazkaMySuffixDetailComponent,
    KsiazkaMySuffixUpdateComponent,
    KsiazkaMySuffixDeletePopupComponent,
    KsiazkaMySuffixDeleteDialogComponent,
    ksiazkaRoute,
    ksiazkaPopupRoute
} from './';

const ENTITY_STATES = [...ksiazkaRoute, ...ksiazkaPopupRoute];

@NgModule({
    imports: [BibliotekaTimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        KsiazkaMySuffixComponent,
        KsiazkaMySuffixDetailComponent,
        KsiazkaMySuffixUpdateComponent,
        KsiazkaMySuffixDeleteDialogComponent,
        KsiazkaMySuffixDeletePopupComponent
    ],
    entryComponents: [
        KsiazkaMySuffixComponent,
        KsiazkaMySuffixUpdateComponent,
        KsiazkaMySuffixDeleteDialogComponent,
        KsiazkaMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimKsiazkaMySuffixModule {}
