/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';

import { CyberholocampusTestModule } from '../../../test.module';
import { AffectationDetailComponent } from '../../../../../../main/webapp/app/entities/affectation/affectation-detail.component';
import { AffectationService } from '../../../../../../main/webapp/app/entities/affectation/affectation.service';
import { Affectation } from '../../../../../../main/webapp/app/entities/affectation/affectation.model';

describe('Component Tests', () => {

    describe('Affectation Management Detail Component', () => {
        let comp: AffectationDetailComponent;
        let fixture: ComponentFixture<AffectationDetailComponent>;
        let service: AffectationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [AffectationDetailComponent],
                providers: [
                    AffectationService
                ]
            })
            .overrideTemplate(AffectationDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AffectationDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffectationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                spyOn(service, 'find').and.returnValue(Observable.of(new HttpResponse({
                    body: new Affectation(123)
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.find).toHaveBeenCalledWith(123);
                expect(comp.affectation).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
