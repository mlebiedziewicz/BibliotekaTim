import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { AutorMySuffix } from 'app/shared/model/autor-my-suffix.model';
import { AutorMySuffixService } from './autor-my-suffix.service';
import { AutorMySuffixComponent } from './autor-my-suffix.component';
import { AutorMySuffixDetailComponent } from './autor-my-suffix-detail.component';
import { AutorMySuffixUpdateComponent } from './autor-my-suffix-update.component';
import { AutorMySuffixDeletePopupComponent } from './autor-my-suffix-delete-dialog.component';
import { IAutorMySuffix } from 'app/shared/model/autor-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class AutorMySuffixResolve implements Resolve<IAutorMySuffix> {
    constructor(private service: AutorMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<AutorMySuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<AutorMySuffix>) => response.ok),
                map((autor: HttpResponse<AutorMySuffix>) => autor.body)
            );
        }
        return of(new AutorMySuffix());
    }
}

export const autorRoute: Routes = [
    {
        path: 'autor-my-suffix',
        component: AutorMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bibliotekaTimApp.autor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'autor-my-suffix/:id/view',
        component: AutorMySuffixDetailComponent,
        resolve: {
            autor: AutorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.autor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'autor-my-suffix/new',
        component: AutorMySuffixUpdateComponent,
        resolve: {
            autor: AutorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.autor.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'autor-my-suffix/:id/edit',
        component: AutorMySuffixUpdateComponent,
        resolve: {
            autor: AutorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.autor.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const autorPopupRoute: Routes = [
    {
        path: 'autor-my-suffix/:id/delete',
        component: AutorMySuffixDeletePopupComponent,
        resolve: {
            autor: AutorMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.autor.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
