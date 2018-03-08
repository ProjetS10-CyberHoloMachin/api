/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Observable';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { CyberholocampusTestModule } from '../../../test.module';
import { AffectationComponent } from '../../../../../../main/webapp/app/entities/affectation/affectation.component';
import { AffectationService } from '../../../../../../main/webapp/app/entities/affectation/affectation.service';
import { Affectation } from '../../../../../../main/webapp/app/entities/affectation/affectation.model';

describe('Component Tests', () => {

    describe('Affectation Management Component', () => {
        let comp: AffectationComponent;
        let fixture: ComponentFixture<AffectationComponent>;
        let service: AffectationService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [CyberholocampusTestModule],
                declarations: [AffectationComponent],
                providers: [
                    AffectationService
                ]
            })
            .overrideTemplate(AffectationComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AffectationComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AffectationService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new HttpHeaders().append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of(new HttpResponse({
                    body: [new Affectation(123)],
                    headers
                })));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.affectations[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
