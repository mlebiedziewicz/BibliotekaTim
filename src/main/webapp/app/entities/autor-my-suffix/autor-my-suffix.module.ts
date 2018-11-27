import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BibliotekaTimSharedModule } from 'app/shared';
import {
    AutorMySuffixComponent,
    AutorMySuffixDetailComponent,
    AutorMySuffixUpdateComponent,
    AutorMySuffixDeletePopupComponent,
    AutorMySuffixDeleteDialogComponent,
    autorRoute,
    autorPopupRoute
} from './';

const ENTITY_STATES = [...autorRoute, ...autorPopupRoute];

@NgModule({
    imports: [BibliotekaTimSharedModule, RouterModule.forChild(ENTITY_STATES)],
    declarations: [
        AutorMySuffixComponent,
        AutorMySuffixDetailComponent,
        AutorMySuffixUpdateComponent,
        AutorMySuffixDeleteDialogComponent,
        AutorMySuffixDeletePopupComponent
    ],
    entryComponents: [
        AutorMySuffixComponent,
        AutorMySuffixUpdateComponent,
        AutorMySuffixDeleteDialogComponent,
        AutorMySuffixDeletePopupComponent
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BibliotekaTimAutorMySuffixModule {}
