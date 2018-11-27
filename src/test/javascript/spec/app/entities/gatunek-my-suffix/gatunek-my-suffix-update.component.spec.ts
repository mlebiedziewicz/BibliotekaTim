/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { GatunekMySuffixUpdateComponent } from 'app/entities/gatunek-my-suffix/gatunek-my-suffix-update.component';
import { GatunekMySuffixService } from 'app/entities/gatunek-my-suffix/gatunek-my-suffix.service';
import { GatunekMySuffix } from 'app/shared/model/gatunek-my-suffix.model';

describe('Component Tests', () => {
    describe('GatunekMySuffix Management Update Component', () => {
        let comp: GatunekMySuffixUpdateComponent;
        let fixture: ComponentFixture<GatunekMySuffixUpdateComponent>;
        let service: GatunekMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [GatunekMySuffixUpdateComponent]
            })
                .overrideTemplate(GatunekMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(GatunekMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(GatunekMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GatunekMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gatunek = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new GatunekMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.gatunek = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
