import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliotekaTimSharedModule } from 'app/shared';
import { BibliotekaTimAdminModule } from 'app/admin/admin.module';
import {
    WypozyczoneMySuffixComponent,
    WypozyczoneMySuffixDetailComponent,
    WypozyczoneMySuffixUpdateComponent,
    WypozyczoneMySuffixDeletePopupComponent,
    WypozyczoneMySuffixDeleteDialogComponent,
    wypozyczoneRoute,
    wypozyczonePopupRoute
} from './';

const ENTITY_STATES = [...wypozyczoneRoute, ...wypozyczonePopupRoute];

@NgModule({
    imports: [BibliotekaTimSharedModule, BibliotekaTimAdminModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WypozyczoneMySuffixComponent,
        WypozyczoneMySuffixDetailComponent,
        WypozyczoneMySuffixUpdateComponent,
        WypozyczoneMySuffixDeleteDialogComponent,
        WypozyczoneMySuffixDeletePopupComponent
    ],
    entryComponents: [
        WypozyczoneMySuffixComponent,
        WypozyczoneMySuffixUpdateComponent,
        WypozyczoneMySuffixDeleteDialogComponent,
        WypozyczoneMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimWypozyczoneMySuffixModule {}
