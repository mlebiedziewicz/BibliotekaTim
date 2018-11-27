import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { JhiPaginationUtil, JhiResolvePagingParams } from 'ng-jhipster';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { KsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';
import { KsiazkaMySuffixService } from './ksiazka-my-suffix.service';
import { KsiazkaMySuffixComponent } from './ksiazka-my-suffix.component';
import { KsiazkaMySuffixDetailComponent } from './ksiazka-my-suffix-detail.component';
import { KsiazkaMySuffixUpdateComponent } from './ksiazka-my-suffix-update.component';
import { KsiazkaMySuffixDeletePopupComponent } from './ksiazka-my-suffix-delete-dialog.component';
import { IKsiazkaMySuffix } from 'app/shared/model/ksiazka-my-suffix.model';

@Injectable({ providedIn: 'root' })
export class KsiazkaMySuffixResolve implements Resolve<IKsiazkaMySuffix> {
    constructor(private service: KsiazkaMySuffixService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<KsiazkaMySuffix> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<KsiazkaMySuffix>) => response.ok),
                map((ksiazka: HttpResponse<KsiazkaMySuffix>) => ksiazka.body)
            );
        }
        return of(new KsiazkaMySuffix());
    }
}

export const ksiazkaRoute: Routes = [
    {
        path: 'ksiazka-my-suffix',
        component: KsiazkaMySuffixComponent,
        resolve: {
            pagingParams: JhiResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            defaultSort: 'id,asc',
            pageTitle: 'bibliotekaTimApp.ksiazka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ksiazka-my-suffix/:id/view',
        component: KsiazkaMySuffixDetailComponent,
        resolve: {
            ksiazka: KsiazkaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.ksiazka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ksiazka-my-suffix/new',
        component: KsiazkaMySuffixUpdateComponent,
        resolve: {
            ksiazka: KsiazkaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.ksiazka.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'ksiazka-my-suffix/:id/edit',
        component: KsiazkaMySuffixUpdateComponent,
        resolve: {
            ksiazka: KsiazkaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.ksiazka.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const ksiazkaPopupRoute: Routes = [
    {
        path: 'ksiazka-my-suffix/:id/delete',
        component: KsiazkaMySuffixDeletePopupComponent,
        resolve: {
            ksiazka: KsiazkaMySuffixResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'bibliotekaTimApp.ksiazka.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
