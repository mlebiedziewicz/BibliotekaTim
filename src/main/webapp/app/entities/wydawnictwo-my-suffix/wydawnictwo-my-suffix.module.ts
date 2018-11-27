import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliotekaTimSharedModule } from 'app/shared';
import {
    WydawnictwoMySuffixComponent,
    WydawnictwoMySuffixDetailComponent,
    WydawnictwoMySuffixUpdateComponent,
    WydawnictwoMySuffixDeletePopupComponent,
    WydawnictwoMySuffixDeleteDialogComponent,
    wydawnictwoRoute,
    wydawnictwoPopupRoute
} from './';

const ENTITY_STATES = [...wydawnictwoRoute, ...wydawnictwoPopupRoute];

@NgModule({
    imports: [BibliotekaTimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        WydawnictwoMySuffixComponent,
        WydawnictwoMySuffixDetailComponent,
        WydawnictwoMySuffixUpdateComponent,
        WydawnictwoMySuffixDeleteDialogComponent,
        WydawnictwoMySuffixDeletePopupComponent
    ],
    entryComponents: [
        WydawnictwoMySuffixComponent,
        WydawnictwoMySuffixUpdateComponent,
        WydawnictwoMySuffixDeleteDialogComponent,
        WydawnictwoMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimWydawnictwoMySuffixModule {}
