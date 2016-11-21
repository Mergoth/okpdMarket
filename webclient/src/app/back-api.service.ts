import {Injectable} from "@angular/core";
import { environment } from '../environments/environment';
import {Http, Headers, Response, URLSearchParams, RequestOptions} from "@angular/http";
import "./rxjs-operators";

const backendRestUrlRoot = environment.serverUrl;

@Injectable()
export class BackAPI {

  constructor(private http: Http) {
  }

  classificatorTypes(): Promise<any> {
    return this.get('classificators');
  }

  classificatorTree(classificator: string, nodeId: string,  params: Object): Promise<any> {
    return this.get((nodeId ? `classificators/${classificator}/${nodeId}` :  `classificators/${classificator}`), params);
  }

  classificatorsBy(query: string, type: string = null): Promise<any> {
    return this.get(`classificators/${type}/search`, {query: query});
  }

  private get(url: string, params: Object = {}): Promise<any> {
    let headers = new Headers({'Content-Type': 'application/json'});
    let queryParams = this.buildQueryParams(params);
    let options = new RequestOptions({
      headers: headers,
      search: new URLSearchParams(queryParams)
    });
    console.log('GET:', `${backendRestUrlRoot}/${url}?${queryParams}`);
    return this.http.get(`${backendRestUrlRoot}/${url}`, options)
      .toPromise()
      .then(this.extractData)
      .catch(this.handleError);
  }

  private buildQueryParams(params: Object) {
    var pairs: string[] = [];
    var keys: string[] = Object.keys(params || {}).sort();
    var encode = encodeURIComponent;
    var encodeKey = function encodeKey(k: string) {
      return encode(k).replace('%24', '$');
    };

    for (let i = 0, len = keys.length; i < len; i++) {
      const key = keys[i];
      const value = params[key];
      if (value === null || value === undefined) {
        continue;
      }

      if (Array.isArray(value)) {
        const arrayKey = encodeKey(key);
        for (let j = 0, l = value.length; j < l; j++) {
          pairs.push(`${arrayKey}=${encode(value[j])}`);
        }
      } else {
        pairs.push(`${encodeKey(key)}=${encode(value)}`);
      }
    }

    if (pairs.length === 0) {
      return '';
    }
    return `${pairs.join('&')}`;
  }

  private extractData(res: Response) {
    let body = res.json();
    return body || {};
  }

  private handleError(error: any) {
    // In a real world app, we might use a remote logging infrastructure
    // We'd also dig deeper into the error to get a better message
    let errMsg = (error.message) ? error.message :
      error.status ? `${error.status} - ${error.statusText}` : 'Server error';
    console.error(errMsg); // log to console instead
    return Promise.reject(errMsg);
  }


}