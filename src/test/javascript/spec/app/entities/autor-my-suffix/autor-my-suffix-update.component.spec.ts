/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { BibliotekaTimTestModule } from '../../../test.module';
import { AutorMySuffixUpdateComponent } from 'app/entities/autor-my-suffix/autor-my-suffix-update.component';
import { AutorMySuffixService } from 'app/entities/autor-my-suffix/autor-my-suffix.service';
import { AutorMySuffix } from 'app/shared/model/autor-my-suffix.model';

describe('Component Tests', () => {
    describe('AutorMySuffix Management Update Component', () => {
        let comp: AutorMySuffixUpdateComponent;
        let fixture: ComponentFixture<AutorMySuffixUpdateComponent>;
        let service: AutorMySuffixService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [BibliotekaTimTestModule],
                declarations: [AutorMySuffixUpdateComponent]
            })
                .overrideTemplate(AutorMySuffixUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(AutorMySuffixUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AutorMySuffixService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new AutorMySuffix(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.autor = entity;
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
                    const entity = new AutorMySuffix();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.autor = entity;
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
