import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Observable';
import { SERVER_API_URL } from '../../app.constants';

import { EntrepriseMySuffix } from './entreprise-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EntrepriseMySuffixService {

    private resourceUrl =  SERVER_API_URL + 'api/entreprises';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/entreprises';

    constructor(private http: Http) { }

    create(entreprise: EntrepriseMySuffix): Observable<EntrepriseMySuffix> {
        const copy = this.convert(entreprise);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(entreprise: EntrepriseMySuffix): Observable<EntrepriseMySuffix> {
        const copy = this.convert(entreprise);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EntrepriseMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to EntrepriseMySuffix.
     */
    private convertItemFromServer(json: any): EntrepriseMySuffix {
        const entity: EntrepriseMySuffix = Object.assign(new EntrepriseMySuffix(), json);
        return entity;
    }

    /**
     * Convert a EntrepriseMySuffix to a JSON which can be sent to the server.
     */
    private convert(entreprise: EntrepriseMySuffix): EntrepriseMySuffix {
        const copy: EntrepriseMySuffix = Object.assign({}, entreprise);
        return copy;
    }
}
