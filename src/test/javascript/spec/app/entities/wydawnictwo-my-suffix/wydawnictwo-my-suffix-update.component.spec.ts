/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { WydawnictwoMySuffixUpdateComponent } from 'app/entities/wydawnictwo-my-suffix/wydawnictwo-my-suffix-update.component';
import { WydawnictwoMySuffixService } from 'app/entities/wydawnictwo-my-suffix/wydawnictwo-my-suffix.service';
import { WydawnictwoMySuffix } from 'app/shared/model/wydawnictwo-my-suffix.model';

describe('Component Tests', () => {
    describe('WydawnictwoMySuffix Management Update Component', () => {
        let comp: WydawnictwoMySuffixUpdateComponent;
        let fixture: ComponentFixture<WydawnictwoMySuffixUpdateComponent>;
        let service: WydawnictwoMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [WydawnictwoMySuffixUpdateComponent]
            })
                .overrideTemplate(WydawnictwoMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(WydawnictwoMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(WydawnictwoMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new WydawnictwoMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wydawnictwo = entity;
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
                    const entity = new WydawnictwoMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.wydawnictwo = entity;
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
